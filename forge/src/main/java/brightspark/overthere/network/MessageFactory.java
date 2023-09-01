package brightspark.overthere.network;

import brightspark.overthere.OTConstants;
import brightspark.overthere.network.message.Message;
import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class MessageFactory<T extends Message> implements Function<FriendlyByteBuf, T> {
	private final Class<T> messageClass;
	private final Constructor<?> constructor;

	public MessageFactory(Class<T> messageClass, Constructor<?> constructor) {
		this.messageClass = messageClass;
		this.constructor = constructor;
	}

	public static <T extends Message> Function<FriendlyByteBuf, T> create(Class<T> messageClass) {
		Constructor<?>[] constructors = messageClass.getDeclaredConstructors();

		// First try to find constructor with just FriendlyByteBuf as a parameter
		Optional<Constructor<?>> maybeConstructor = Arrays.stream(constructors)
			.filter(c -> c.getParameterCount() == 1 && c.getParameterTypes()[0] == FriendlyByteBuf.class)
			.findFirst();
		if (maybeConstructor.isPresent()) {
			return new MessageFactory<>(messageClass, maybeConstructor.get());
		} else {
			// Else, find a default constructor
			maybeConstructor = Arrays.stream(constructors).filter(c -> c.getParameterCount() == 0).findFirst();
			if (maybeConstructor.isPresent()) {
				return new MessageFactory<>(messageClass, maybeConstructor.get());
			} else {
				// None found, throw exception
				throw new IllegalStateException("Message " + messageClass.getName() + " does not have any suitable constructor!");
			}
		}
	}

	@Override
	public T apply(FriendlyByteBuf buf) {
		try {
			Object instance = constructor.getParameterCount() == 0
				? constructor.newInstance()
				: constructor.newInstance(buf);
			return messageClass.cast(instance);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			OTConstants.LOG.error(
				"Error instantiating message " + messageClass.getName() + " using constructor: " + constructor,
				e
			);
			return null;
		}
	}
}
