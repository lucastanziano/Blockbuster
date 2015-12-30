package lucastanziano.blockbuster.di;

import dagger.Component;
import lucastanziano.blockbuster.view.main.MainActivity;

/**
 * Created by Luca on 07/11/2015.
 */
@Component(dependencies = NavigationModule.class)
public interface NavigationComponent {
    MainActivity inject(MainActivity activity);

}
