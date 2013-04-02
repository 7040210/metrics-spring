/*
 * Copyright 2012 Ryan W Tenney (http://ryan.10e.us)
 *            and Martello Technologies (http://martellotech.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ryantenney.metrics.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import com.yammer.metrics.annotation.ExceptionMetered;
import com.yammer.metrics.MetricRegistry;

public class ExceptionMeteredAnnotationBeanPostProcessor extends AbstractProxyingBeanPostProcessor {

	private static final long serialVersionUID = -1967025297766933304L;

	private final Pointcut pointcut = new AnnotationMatchingPointcut(null, ExceptionMetered.class);
	private final MetricRegistry metrics;

	public ExceptionMeteredAnnotationBeanPostProcessor(final MetricRegistry metrics, final ProxyConfig config) {
		this.metrics = metrics;

		this.copyFrom(config);
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

	@Override
	public MethodInterceptor getMethodInterceptor(Class<?> targetClass) {
		return new ExceptionMeteredMethodInterceptor(metrics, targetClass);
	}

}
