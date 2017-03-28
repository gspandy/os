package com.hitler.core.aop.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.OptimisticLockException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { METHOD })
public @interface RetriableTransaction {

	int maxRetries() default 100;
	
	Class<? extends Throwable>[] retryFor() default {ObjectOptimisticLockingFailureException.class, OptimisticLockException.class};
	
}
