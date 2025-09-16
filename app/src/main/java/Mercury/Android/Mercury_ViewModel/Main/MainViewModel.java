package Mercury.Android.Mercury_ViewModel.Main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Mercury.Android.Mercury_Model.Entitys.Entity_02_Chat_Session;
import Mercury.Android.Mercury_Repository.MainRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private final MainRepository mainRepository;

    private final MutableLiveData<List<Entity_02_Chat_Session>> _chatList = new MutableLiveData<>();
    public LiveData<List<Entity_02_Chat_Session>> chatList = _chatList;


    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> error = _error;

    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }


    public void fetchAllLives() {
        mainRepository.getGroupList().enqueue(new Callback<List<Entity_02_Chat_Session>>() {
            @Override
            public void onResponse(Call<List<Entity_02_Chat_Session>> call, Response<List<Entity_02_Chat_Session>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _chatList.postValue(response.body());
                } else {
                    _error.postValue("Erro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Entity_02_Chat_Session>> call, Throwable t) {
                _error.postValue("Falha na requisição: " + t.getMessage());
            }
        });
    }
}
