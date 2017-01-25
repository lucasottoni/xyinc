package br.com.zup.teste.xyinc.business.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import br.com.zup.teste.xyinc.business.exception.BusinessException;
import br.com.zup.teste.xyinc.business.message.MessageResponse;
import br.com.zup.teste.xyinc.business.message.MessageUtils;

/**
 * BusinessExceptionHandlerAspect
 * Aspect that handles every exception generated on business layer.
 * Convert them to {@link BusinessException}.
 *
 */
@Aspect
@Component
public class BusinessExceptionHandlerAspect {

	private static final String ERROR_UNEXPECTED = "error.unexpected";

	/**
	 * Intercepts methods from business classes and convert its exceptions to BusinessException.
	 *
	 * @param joinPoint - Method being executed
	 * @return Object - If the method has a MessageResponse return value and throws an exception, creates one and
	 *         responds. Otherwise, the return from the method.
	 * @throws BusinessException - If a BusinessException was already thrown, re-throw it.
	 */
	@Around("execution(* br.com.zup.teste.xyinc.business.bo.*BO.*(..))")
	public Object handleAndLogExeptions(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		try {
			return joinPoint.proceed();
		} catch (IllegalArgumentException e) {
			return createMessageResponse(new BusinessException(e), e.getMessage());
		} catch (RuntimeException e) {
			return handleRuntimeException(e, signature.getReturnType().toString());
		} catch (Throwable e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * Creates a MessageResponse with associated exception encapsulated.
	 *
	 * @param ex - Catched exception.
	 * @param message - Message retrieved.
	 * @return MessageResponse - Response for the method.
	 */
	private MessageResponse<?> createMessageResponse(Exception ex, String message) {
		MessageResponse<?> messageResponse = new MessageResponse<>();
		messageResponse.addErrorMessage(MessageUtils.getMessage(message));
		messageResponse.setEncapsulatedException(ex);
		return messageResponse;
	}

	/**
	 * Handles unexpected runtime exceptions.
	 *
	 * @param ex - RuntimeException.
	 * @param signature - Method signature.
	 * @return MessageResponse - If the called method has it as its return type.
	 */
	private MessageResponse<?> handleRuntimeException(Exception ex, String signature) {
		if (signature.contains("MessageResponse")) {
			return createMessageResponse(new BusinessException(ex), ERROR_UNEXPECTED);
		} else {
			throw new BusinessException(ex);
		}
	}

}
