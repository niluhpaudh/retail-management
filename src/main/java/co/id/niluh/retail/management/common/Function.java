package co.id.niluh.retail.management.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class Function {
	static final ImmutableMap<Long, String> ROMAN_MONTHS = ImmutableMap.<Long, String>builder()
			.put(1L, "I").put(2L, "II").put(3L, "III").put(4L, "IV").put(5L, "V")
			.put(6L, "VI").put(7L, "VII").put(8L, "VIII").put(9L, "IX").put(10L, "X")
			.put(11L, "XI").put(12L, "XII").build();
	
    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class, 
        		new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
    
    public static <T> Predicate<T> distinctByKey(java.util.function.Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
    public static String getReferenceId(String id){
    	return new BigInteger(id).toString(32).toUpperCase();
    }
    
    public static String randomPassword() {
		return RandomStringUtils.randomAlphanumeric(8).replace("l", "a")
				.replace("I", "a").replace("0", "a").replace("O", "a");
	}
    
    public static String randomUsername() {
		return RandomStringUtils.randomAlphabetic(7).replace("l", "a")
				.replace("I", "a").replace("0", "a").replace("O", "a");
	}
    
    public static LocalDateTime longToLocalDateTime(Long l) {
    	return LocalDateTime.ofInstant(Instant.ofEpochSecond(l), TimeZone.getDefault().toZoneId());   
    }
    
    public static InetAddress getLocalIpAddress(String accessToUri){
        final URI uri = URI.create(accessToUri);
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(uri.getHost(), uri.getPort()), 3000);
            return socket.getLocalAddress();
        } catch (IOException e) {
            try {
                return InetAddress.getLocalHost();
            } catch (UnknownHostException unknownHostException) {
                return InetAddress.getLoopbackAddress();
            }
        }
    }

    public static String getLocalIpAddress(){
    	try {
    		return InetAddress.getLocalHost().getHostAddress();
    	} catch (UnknownHostException unknownHostException) {
    		return InetAddress.getLoopbackAddress().getHostAddress();
    	}
    }
    
    public static String getIpAddress(HttpServletRequest httpServletRequest) {
		String clientIp = null;
		try {
			clientIp = extractIp(httpServletRequest);
		}catch (Exception ignored){}
		return clientIp;
	}
	
	public static String getIpAddress() {
		String clientIp = null;
		try {
			HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			clientIp = extractIp(httpServletRequest);
		}catch (Exception ignored){}
		return clientIp;
	}

	public static String extractIp(HttpServletRequest httpServletRequest) {
		String clientIp;
		String clientXForwardedForIp = httpServletRequest.getHeader("x-forwarded-for");
		if (Objects.nonNull(clientXForwardedForIp)) {
			clientIp = parseXForwardedHeader(clientXForwardedForIp);
		} else {
			clientIp = httpServletRequest.getRemoteAddr();
		}
		return clientIp;
	}

	private static String parseXForwardedHeader(String header) {
		return header.split(" *, *")[0];
	}
	
    public static String authHmacSha1(String reqId, String ipAddress, String key, LocalDateTime now){
		try {
			final String time = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss").format(now);
	        final HmacUtils hmacUtils = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key);
	        return hmacUtils.hmacHex(reqId+ipAddress+time);
		} catch (Exception e) {
			throw new RuntimeException("Akses Tidak diizinkan");
		}
	}
    
    public static File loadFiles(String path, Boolean system){
    	try {
    		Path paths = Paths.get(path).toAbsolutePath().normalize();
			if (system) {
				paths = Paths.get(Function.class.getResource(path).toURI()).toAbsolutePath().normalize();
			}
			File file = new File(paths.toUri());
			if (!file.isFile()) {
				throw new RuntimeException("File tidak ditemukan");
			}
			return file;
        } catch (Exception e) {
        	throw new RuntimeException("File tidak ditemukan");
        }
    }
}
