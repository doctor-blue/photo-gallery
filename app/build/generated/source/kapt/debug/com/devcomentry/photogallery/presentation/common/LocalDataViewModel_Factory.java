package com.devcomentry.photogallery.presentation.common;

import android.app.Application;
import com.devcomentry.photogallery.domain.use_case.file.FileUseCases;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class LocalDataViewModel_Factory implements Factory<LocalDataViewModel> {
  private final Provider<Application> applicationProvider;

  private final Provider<FileUseCases> fileUseCasesProvider;

  public LocalDataViewModel_Factory(Provider<Application> applicationProvider,
      Provider<FileUseCases> fileUseCasesProvider) {
    this.applicationProvider = applicationProvider;
    this.fileUseCasesProvider = fileUseCasesProvider;
  }

  @Override
  public LocalDataViewModel get() {
    return newInstance(applicationProvider.get(), fileUseCasesProvider.get());
  }

  public static LocalDataViewModel_Factory create(Provider<Application> applicationProvider,
      Provider<FileUseCases> fileUseCasesProvider) {
    return new LocalDataViewModel_Factory(applicationProvider, fileUseCasesProvider);
  }

  public static LocalDataViewModel newInstance(Application application, FileUseCases fileUseCases) {
    return new LocalDataViewModel(application, fileUseCases);
  }
}
