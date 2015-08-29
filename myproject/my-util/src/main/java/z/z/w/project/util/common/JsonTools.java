/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.common.JsonTools.java
 *         Desc: Jackson工具类，提供Map或Entity与Json的互相转换, 性能比Gson高5~10倍
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-18 上午10:19:29 
 *   LastChange: 2013-9-18 上午10:19:29 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;

/**
 * z.z.w.project.util.common.JsonTools.java
 */
public abstract class JsonTools {

	private static final ObjectMapper mapper = new ObjectMapper();

	static {

		// 使用单引号
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.configure(
				DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 禁止使用int代表Enum的order()來反序列化Enum,非常危險
		mapper.configure(
				DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
	}

	/**
	 * 将Map接口类型序列化为Json字符串 <br>
	 * Created on: 2013-9-18 上午11:38:22
	 * 
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map<?, ?> map) {
		StringWriter stringWriter = null;
		JsonGenerator jsonGenerator = null;
		try {
			// 采用StringWriter方式可以大大提高转换的性能
			stringWriter = new StringWriter();
			jsonGenerator = new JsonFactory().createJsonGenerator(stringWriter);
			mapper.writeValue(jsonGenerator, map);
			String actual = stringWriter.toString();

			jsonGenerator.close();
			jsonGenerator = null;
			stringWriter.close();
			stringWriter = null;

			return actual;
		} catch (Exception e) {
			return null;
		} finally {
			if (!DataTools.isEmpty(jsonGenerator))
				try {
					jsonGenerator.close();
					jsonGenerator = null;
				} catch (IOException e) {
				} finally {
					jsonGenerator = null;
				}
			if (!DataTools.isEmpty(stringWriter))
				try {
					stringWriter.close();
					stringWriter = null;
				} catch (IOException e) {
				} finally {
					stringWriter = null;
				}
		}
	}

	/**
	 * 将Json字符串反序列化为Map接口类型 <br>
	 * Created on: 2013-9-18 上午11:41:10
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<?, ?> jsonToMap(String jsonString) {
		try {
			return mapper.readValue(jsonString, LinkedHashMap.class);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将实体序列化为Json字符串 <br>
	 * Created on: 2013-9-18 上午11:43:06
	 * 
	 * @param entity
	 *            传入要转换的实体，支持List
	 * @return
	 */
	public static String entityToJson(Object entity) {

		StringWriter stringWriter = null;
		JsonGenerator jsonGenerator = null;

		try {
			stringWriter = new StringWriter();
			jsonGenerator = new JsonFactory().createJsonGenerator(stringWriter);
			mapper.writeValue(jsonGenerator, entity);
			String actual = stringWriter.toString();

			jsonGenerator.close();
			jsonGenerator = null;
			stringWriter.close();
			stringWriter = null;

			return actual;
		} catch (Exception e) {
			return null;
		} finally {
			if (!DataTools.isEmpty(jsonGenerator))
				try {
					jsonGenerator.close();
					jsonGenerator = null;
				} catch (IOException e) {
				} finally {
					jsonGenerator = null;
				}
			if (!DataTools.isEmpty(stringWriter))
				try {
					stringWriter.close();
					stringWriter = null;
				} catch (IOException e) {
				} finally {
					stringWriter = null;
				}
		}
	}

	/**
	 * 将JSON反序列化为实体或实体集合 <br>
	 * Created on: 2013-9-18 上午11:45:19
	 * 
	 * @param <T>
	 * @param jsonString
	 *            Json字符串
	 * @param typeRef
	 *            目标类型
	 * @return 类型为T的实体或实体集合
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToEntity(String jsonString, TypeReference<?> typeRef) {
		try {
			return ((T) mapper.readValue(jsonString, typeRef));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将JSON转换为Jackson的ObjectNode <br>
	 * Created on: 2013-9-18 上午11:46:25
	 * 
	 * @param jsonString
	 *            Json字符串
	 * @return
	 */
	public static ObjectNode jsonToJsonNode(String jsonString) {
		try {
			return mapper.readValue(jsonString, ObjectNode.class);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将Map转换为Jackson的ObjectNode
	 * 
	 * <br>
	 * Created on: 2013-9-18 上午11:49:53
	 * 
	 * @param map
	 * @return
	 */
	public static ObjectNode toJsonNode(Map<?, ?> map) {
		try {
			return jsonToJsonNode(mapToJson(map));
		} catch (Exception e) {
			return null;
		}
	}
}
