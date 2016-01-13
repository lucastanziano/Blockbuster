package lucastanziano.blockbuster.data.remote;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


/**
 * Created by Luca on 31/12/2015.
 */
public class ServiceFactory {

    public static TMDBService newTMDBService(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(logging);

        Retrofit movieAPIRest = new Retrofit.Builder()
                .baseUrl(TMDBService.MOVIE_DB_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient)
                .build();

        return movieAPIRest.create(TMDBService.class);
    }
}
