package com.devcomentry.photogallery.presentation.photo_detail;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class PhotoDetailViewModel_Factory implements Factory<PhotoDetailViewModel> {
  @Override
  public PhotoDetailViewModel get() {
    return newInstance();
  }

  public static PhotoDetailViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PhotoDetailViewModel newInstance() {
    return new PhotoDetailViewModel();
  }

  private static final class InstanceHolder {
    private static final PhotoDetailViewModel_Factory INSTANCE = new PhotoDetailViewModel_Factory();
  }
}
