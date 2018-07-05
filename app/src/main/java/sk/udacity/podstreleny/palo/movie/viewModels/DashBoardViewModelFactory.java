package sk.udacity.podstreleny.palo.movie.viewModels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class DashBoardViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public DashBoardViewModelFactory(Application application){
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DashBoardViewModel(application);
    }
}
