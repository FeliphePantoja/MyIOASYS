package br.com.myapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.myapplication.model.Catalog;
import br.com.myapplication.model.Enterprise;
import br.com.myapplication.repository.Authentication;
import br.com.myapplication.service.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoListViewModel extends ViewModel {

    private ClientService clientService = new ClientService();

    private Enterprise _catalog = new Enterprise();

    public MutableLiveData<String> mEnterprise_name = new MutableLiveData<>();
    public MutableLiveData<String> mDescription = new MutableLiveData<>();
    public MutableLiveData<String> mCity = new MutableLiveData<>();
    public MutableLiveData<String> mCountry = new MutableLiveData<>();
    public MutableLiveData<String> mPhoto = new MutableLiveData<>();

    public MutableLiveData<List<Enterprise>> mList = new MutableLiveData<>();
    public LiveData<List<Enterprise>> getList = this.mList;

    private void Update(Enterprise catalog) {
        _catalog = catalog;
        mEnterprise_name.setValue(_catalog.getEnterprise_name());
        mDescription.setValue(_catalog.getDescription());
        mCity.setValue(_catalog.getCity());
        mCountry.setValue(_catalog.getCountry());
        mPhoto.setValue(_catalog.getPhoto());
    }

    public Enterprise GetEnterprise(){
        _catalog.setEnterprise_name(mEnterprise_name.getValue());
        _catalog.setDescription(mDescription.getValue());
        _catalog.setCity(mCity.getValue());
        _catalog.setCountry(mCountry.getValue());
        _catalog.setPhoto(mPhoto.getValue());
        return _catalog;
    }

    public void Catalog(){

        Call<Enterprise> enterpriseCall = clientService.API().listEnterprises(Authentication.token, Authentication.client, Authentication.uid);
        enterpriseCall.enqueue(new Callback<Enterprise>() {
            @Override
            public void onResponse(Call<Enterprise> call, Response<Enterprise> response) {
                assert response.body() != null;
                mList.setValue(response.body().getEnterprises());
            }

            @Override
            public void onFailure(Call<Enterprise> call, Throwable t) {
                Log.i("ERROR", t.getMessage());
            }
        });
    }
}
