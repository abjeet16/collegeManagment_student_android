package com.example.attendanceappstudent.network

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.attendanceappstudent.data_class.UserLoginRequest
import com.example.attendanceappstudent.data_class.UserLoginResponse
import com.example.attendanceappstudent.data_class.UserProfile
import com.example.attendanceappstudent.data_class.StudentAttendance
import com.example.attendanceappstudent.helper.ApiLinkHelper
import com.google.gson.Gson
import org.json.JSONObject

class authApiClient private constructor(context: Context) {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context.applicationContext)
    private val apiLinkHelper = ApiLinkHelper()

    companion object {
        @Volatile
        private var INSTANCE: authApiClient? = null

        fun getInstance(context: Context): authApiClient {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: authApiClient(context).also { INSTANCE = it }
            }
        }
    }
    fun loginUser(
        user: UserLoginRequest,
        onSuccess: (UserLoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        // Create a JSON object for the request body
        val jsonBody = JSONObject().apply {
            put("uucms_id", user.uucmsId)
            put("password", user.password)
        }

        // Create a JsonObjectRequest
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.POST, apiLinkHelper.loginUserApiUri(), jsonBody,
            { response ->
                Log.e("ApiClient", response.toString())
                try {
                    // Parse the JSON response using Gson
                    val loginResponse =
                        Gson().fromJson(response.toString(), UserLoginResponse::class.java)
                    onSuccess(loginResponse)
                } catch (e: Exception) {
                    Log.e("ApiClient", "Error parsing login response: ${e.message}")
                    onError("Error parsing response")
                }
            },
            { error ->
                if (error.networkResponse != null) {
                    val statusCode = error.networkResponse.statusCode
                    val errorResponse = String(error.networkResponse.data)

                    Log.e("ApiClient", "Status Code: $statusCode")
                    Log.e("ApiClient", "Error Response: $errorResponse")

                    onError("Error $statusCode: $errorResponse")
                } else {
                    Log.e("ApiClient", "Error during login: ${error.message}")
                    onError(error.message ?: "Unknown network error occurred")
                }
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        // Add the request to the request queue
        requestQueue.add(jsonObjectRequest)
    }

    fun getUserProfile(
        token: String,
        onSuccess: (UserProfile) -> Unit,
        onError: (String) -> Unit
    ) {
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,  // The method type, GET in this case
            apiLinkHelper.getUserProfileApiUri(),  // Your API URL
            null,  // No body for GET requests
            { response ->  // Success listener
                // Parse the response
                try {
                    val userProfile = Gson().fromJson(response.toString(), UserProfile::class.java)
                    Log.d("UserProfile", "User Profile: ${userProfile.first_name}, ${userProfile.email}")
                    onSuccess(userProfile)
                } catch (e: Exception) {
                    onError("Failed to parse the server response.")
                }
            },
            { error ->  // Error listener
                if (error.networkResponse != null) {
                    val errorResponse = String(error.networkResponse.data)
                    Log.e("ApiClient", "Error Response: $errorResponse")
                    onError(errorResponse)
                } else {
                    Log.e("ApiClient", "Unknown Error: ${error.message}")
                    onError(error.message ?: "Unknown error occurred.")
                }
            }
        ) {
            // Add Authorization header with Bearer token
            override fun getHeaders(): Map<String, String> {
                val headers = mutableMapOf<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        requestQueue.add(jsonObjectRequest) // Add the request to the queue
    }

    fun getUserAttendance(
        token: String,
        onSuccess: (StudentAttendance) -> Unit,
        onError: (String) -> Unit
    ) {
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,  // The method type, GET in this case
            apiLinkHelper.getStudentAttendanceApiUri(),  // Your API URL
            null,  // No body for GET requests
            { response ->  // Success listener
                // Parse the response
                try {
                    val studentAttendance = Gson().fromJson(response.toString(), StudentAttendance::class.java)
                    onSuccess(studentAttendance)
                } catch (e: Exception) {
                    onError("Failed to parse the server response.")
                }
            },
            { error ->  // Error listener
                if (error.networkResponse != null) {
                    val errorResponse = String(error.networkResponse.data)
                    Log.e("ApiClient", "Error Response: $errorResponse")
                    onError(errorResponse)
                } else {
                    Log.e("ApiClient", "Unknown Error: ${error.message}")
                    onError(error.message ?: "Unknown error occurred.")
                }
            }
        ) {
            // Add Authorization header with Bearer token
            override fun getHeaders(): Map<String, String> {
                val headers = mutableMapOf<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        requestQueue.add(jsonObjectRequest) // Add the request to the queue
    }
}