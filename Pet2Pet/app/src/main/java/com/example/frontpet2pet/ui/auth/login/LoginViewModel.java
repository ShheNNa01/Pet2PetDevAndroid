package com.example.frontpet2pet.ui.auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.frontpet2pet.api.ApiClient;
import com.example.frontpet2pet.data.local.SharedPrefsManager;
import com.example.frontpet2pet.data.models.request.LoginRequest;
import com.example.frontpet2pet.data.models.response.AuthResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<AuthResponse> loginResult = new MutableLiveData<>();

    public void login(String email, String password) {
        isLoading.setValue(true);

        ApiClient.getInstance().getApi().login(email, password)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        isLoading.setValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            AuthResponse authResponse = response.body();
                            SharedPrefsManager.getInstance().saveToken(authResponse.getAccessToken());
                            loginResult.setValue(authResponse);
                        } else {
                            try {
                                JSONObject errorBody = new JSONObject(response.errorBody().string());
                                error.setValue(errorBody.getString("detail"));
                            } catch (Exception e) {
                                error.setValue("Error en el inicio de sesión");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        isLoading.setValue(false);
                        error.setValue("Error de conexión: " + t.getMessage());
                    }
                });
    }

    // Getters para los LiveData
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }
    public LiveData<AuthResponse> getLoginResult() { return loginResult; }
}
