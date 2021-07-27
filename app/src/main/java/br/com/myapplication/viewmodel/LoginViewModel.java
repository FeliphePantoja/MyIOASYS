package br.com.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.myapplication.model.User;
import br.com.myapplication.repository.Authentication;
import br.com.myapplication.service.ClientService;
import br.com.myapplication.service.UserService;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private ClientService clientService = new ClientService();
    private UserService service = clientService.API();

    private MutableLiveData<String> mLogin = new MutableLiveData<>();
    public LiveData<String> login = this.mLogin;

    public void Login(String user, String password) {

        User newUser = new User(user, password);
        Call<User> resultUser = this.service.userLogin(newUser);

        resultUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    mLogin.setValue("Email ou senha incorretos");
                } else {
                    mLogin.setValue("");
                    getHeaders(response.headers());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mLogin.setValue("Erro na conexão");
            }
        });
    }

    private void getHeaders(Headers headers) {

        Authentication.token = headers.get("access-token");
        Authentication.client = headers.get("client");
        Authentication.uid = headers.get("uid");
    }
}
