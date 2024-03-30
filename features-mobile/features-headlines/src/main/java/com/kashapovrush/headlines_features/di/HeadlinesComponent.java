package com.kashapovrush.headlines_features.di;

import android.app.Application;
import android.content.Context;

import com.kashapovrush.headlines_features.presentation.ui.HeadlinesFragment;
import com.kashapovrush.headlines_features.presentation.ui.tabs.BusinessTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.EntertainmentTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.GeneralTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.HealthTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.ScienceTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.SportTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.TechnologyTab;
import com.kashapovrush.utils.ApplicationScope.ApplicationScope;

import dagger.BindsInstance;
import dagger.Component;

@ApplicationScope
@Component(modules = {HeadlinesModule.class, ViewModelModule.class})
public interface HeadlinesComponent {

    void inject(HeadlinesFragment fragment);
    void inject(GeneralTab fragment);
    void inject(SportTab fragment);
    void inject(EntertainmentTab fragment);
    void inject(ScienceTab fragment);
    void inject(BusinessTab fragment);
    void inject(TechnologyTab fragment);
    void inject(HealthTab fragment);

    @Component.Factory
    interface Factory {
        HeadlinesComponent create(@BindsInstance Context context, @BindsInstance Application application);
    }
}
