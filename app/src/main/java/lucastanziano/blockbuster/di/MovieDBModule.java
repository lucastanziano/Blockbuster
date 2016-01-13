package lucastanziano.blockbuster.di;

import dagger.Module;
import dagger.Provides;
import lucastanziano.blockbuster.data.remote.ServiceFactory;
import lucastanziano.blockbuster.data.remote.TMDBService;

/**
 * Created by Luca on 31/12/2015.
 */
@Module
public class MovieDBModule {

    public MovieDBModule(){}

    @Provides
    TMDBService providesTMDBAPI(){
        return ServiceFactory.newTMDBService();
    }
}
