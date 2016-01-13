package lucastanziano.blockbuster.di;

import dagger.Component;
import lucastanziano.blockbuster.data.MovieRepository;

/**
 * Created by Luca on 31/12/2015.
 */
@Component(dependencies = MovieDBModule.class)
public interface MovieDBComponent {
    void inject(MovieRepository movieRepository);

}
