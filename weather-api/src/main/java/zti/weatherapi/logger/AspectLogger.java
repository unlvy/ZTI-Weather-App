package zti.weatherapi.logger;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * AOP Logger class.
 * Based on
 * https://www.javaguides.net/2019/05/spring-boot-spring-aop-logging-example-tutorial.html
 */
@Aspect
@Component
public class AspectLogger {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  /**
   * Pointcut that matches all repositories, services and Web REST endpoints.
   */
  @Pointcut("within(@org.springframework.stereotype.Repository *)"
      + " || within(@org.springframework.stereotype.Service *)"
      + " || within(@org.springframework.web.bind.annotation.RestController *)")
  public void repositoryServiceControllerPointcut() {
  }

  /**
   * Pointcut that matches all files in application main packages.
   */
  @Pointcut("within(zti.weatherapi..*)")
  public void applicationPackagePointcut() {
  }

  /**
   * Advice that logs methods throwing exceptions.
   *
   * @param joinPoint join point for advice
   * @param e         exception
   */
  @AfterThrowing(pointcut = "repositoryServiceControllerPointcut() && applicationPackagePointcut()",
      throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    log.error("Exception in {}.{}() with cause = {}",
        joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(),
        e.getCause() != null ? e.getCause() : "NULL");
  }

  /**
   * Advice that logs when a method is entered and exited.
   *
   * @param joinPoint join point for advice
   * @return result
   * @throws Throwable throws IllegalArgumentException
   */
  @Around("repositoryServiceControllerPointcut() && applicationPackagePointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    log.debug("Enter: {}.{}() with argument[s] = {}",
        joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(),
        Arrays.toString(joinPoint.getArgs()));
    try {
      Object result = joinPoint.proceed();
      log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(), result);
      return result;
    } catch (IllegalArgumentException e) {
      log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
      throw e;
    }
  }
}