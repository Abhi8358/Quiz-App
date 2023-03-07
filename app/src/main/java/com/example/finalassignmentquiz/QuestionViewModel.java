package com.example.finalassignmentquiz;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionViewModel extends AndroidViewModel
        implements Response.Listener<String>, Response.ErrorListener{


    private static final String API_URL = "https://raw.githubusercontent.com/tVishal96/sample-english-mcqs/master/db.json";
    private static final String RESPONSE_QUESTIONS_KEY = "questions";
    private static final String RESPONSE_QUESTION_KEY = "question";
    private static final String RESPONSE_ID_KEY = "id";
    private static final String RESPONSE_OPTIONS = "options";
    private static final String RESPONSE_ANS = "correct_option";

    public MutableLiveData<List<Question>> QuestionLiveData = new MutableLiveData<>();
    private MutableLiveData<RequestStatus> requestStatusLiveData = new MutableLiveData<>();


    static boolean onquestionListSecreen;


    static int counter=600;

    static int position;
    static List<Question> questionlist;

    private RequestQueue queue;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        queue = Volley.newRequestQueue(application);
        requestStatusLiveData.postValue(RequestStatus.IN_PROGRESS);

        fetchquestions();
    }



    /**
     * Start re-fetching Questions list from service.
     */
    public void refetchquestions() {
        requestStatusLiveData.postValue(RequestStatus.IN_PROGRESS);
        fetchquestions();
    }

    /**
     * @return the {@link LiveData} instance containing list of Question.
     */
    public LiveData<List<Question>> getCountryLiveData() {
        return QuestionLiveData;
    }

    /**
     * @return the {@link LiveData} instance containing request status of question list API.
     */
    public LiveData<RequestStatus> getRequestStatusLiveData() {
        return requestStatusLiveData;
    }

    @Override
    public void onResponse(String response) {
        try {
            List<Question> QuestionModels = parseResponse(response);
            QuestionLiveData.postValue(QuestionModels);
            requestStatusLiveData.postValue(RequestStatus.SUCCEEDED);
        } catch (JSONException e) {
            e.printStackTrace();
            requestStatusLiveData.postValue(RequestStatus.FAILED);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        requestStatusLiveData.postValue(RequestStatus.FAILED);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    private void fetchquestions() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL, this, this);
        queue.add(stringRequest);
    }

    private List<Question> parseResponse(String response) throws JSONException {

        List<Question> models = new ArrayList<>();
        JSONObject res = new JSONObject(response);
        JSONArray entries = res.optJSONArray(RESPONSE_QUESTIONS_KEY);

        if (entries == null) {
            return models;
        }

        for (int i = 0; i < entries.length(); i++) {
            JSONObject obj = (JSONObject) entries.get(i);
            String question = obj.optString(RESPONSE_QUESTION_KEY);
            int id = obj.getInt(RESPONSE_ID_KEY);
            int ans = obj.getInt(RESPONSE_ANS);
            ArrayList<String>option = new ArrayList<>();

            JSONArray jsonArray = obj.getJSONArray(RESPONSE_OPTIONS);
            for(int j=0;j<4;j++){
                option.add(jsonArray.getString(j));
            }
            Question model = new Question(id,question,option,ans);
            models.add(model);
        }

        return models;
    }

    /**
     * Enum class to define status of question list API request.
     */
    public enum RequestStatus {
        /* Show API is in progress. */
        IN_PROGRESS,

        /* Show API request is failed. */
        FAILED,

        /* Show API request is successfully completed. */
        SUCCEEDED
    }

}
