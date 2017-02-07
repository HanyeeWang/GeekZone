package com.hanyee.geekzone.di.annotation;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Hanyee on 16/10/10.
 */

@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
