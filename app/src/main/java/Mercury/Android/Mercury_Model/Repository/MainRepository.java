package Mercury.Android.Mercury_Model.Repository;

import Mercury.Android.Mercury_Model.Retrofit.RetrofitService;
import Mercury.Android.Mercury_Model.Entitys.Entity_02_Chat_Session;
import retrofit2.Call;
import java.util.List;

public class MainRepository {

    private final RetrofitService retrofitService;

    public MainRepository(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    public Call<List<Entity_02_Chat_Session>> getGroupList() {
        return retrofitService.groupList();
    }
}
