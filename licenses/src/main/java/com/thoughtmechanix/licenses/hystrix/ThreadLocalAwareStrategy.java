package com.thoughtmechanix.licenses.hystrix;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.thoughtmechanix.licenses.utils.UserContextHolder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

  /**
   * Spring Cloud has a concurrency class defined, which we set as existingConcurrencyStrategy.
   * Several methods are overridden to ensure we send to the Spring Cloud's concurrency strategy
   * if it exists, else we send to Hystrix strategy base method.
   */
  private final HystrixConcurrencyStrategy existingConcurrencyStrategy;

  public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy existingConcurrencyStrategy) {
    this.existingConcurrencyStrategy = existingConcurrencyStrategy;
  }

  @Override
  public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
    return existingConcurrencyStrategy != null
        ? existingConcurrencyStrategy.getBlockingQueue(maxQueueSize)
        : super.getBlockingQueue(maxQueueSize);
  }

  @Override
  public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
    return existingConcurrencyStrategy != null
        ? existingConcurrencyStrategy.getRequestVariable(rv)
        : super.getRequestVariable(rv);
  }

  @Override
  public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
      HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize,
      HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
    return existingConcurrencyStrategy != null
        ? existingConcurrencyStrategy.getThreadPool(
            threadPoolKey, corePoolSize, maximumPoolSize,
            keepAliveTime, unit, workQueue
          )
        : super.getThreadPool(
            threadPoolKey, corePoolSize, maximumPoolSize,
            keepAliveTime, unit, workQueue
        );
  }

  /**
   * @param callable Function to be called by Hystrix.
   * @param <T> Return type of the callable
   * @return Callable for delegating user context.
   */
  @Override
  public <T> Callable<T> wrapCallable(Callable<T> callable) {
    DelegatingUserContextCallable<T> delegatingUserContextCallable = new DelegatingUserContextCallable<T>(
        callable,
        UserContextHolder.getContext()
    );

    return existingConcurrencyStrategy != null
        ? existingConcurrencyStrategy.wrapCallable(delegatingUserContextCallable)
        : super.wrapCallable(delegatingUserContextCallable);
  }
}
