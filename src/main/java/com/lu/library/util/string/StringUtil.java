package com.lu.library.util.string;


import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Random;

public class StringUtil {


	public static void formatNumber(double d) {
		final DecimalFormat decimalFormat = new DecimalFormat("#.00");
		decimalFormat.format(d);
	}
	/**
	 *d=0.22545 num=2 return 0.22<br/>
	 *d=0.22545 num=3 return 0.225<br/>
	 *d=0.22545 num=4 return 0.2254<br/>
	 *
	 * @param d 截取2位小数点
	 * @return
	 */
	public static String format2Point(double d){
		return String.valueOf(formatPoint(d,2));
	}
	/**
	 *d=0.22545 num=2 return 0.22
	 *d=0.22545 num=3 return 0.225
	 *d=0.22545 num=4 return 0.2254
	 *
	 * @param d
	 * @param num 保留的小数点位数
	 * @return 截取num位小数点
	 */
	public static double formatPoint(double d,int num){
		double sprt=Math.pow(10, num);
		int tempd=(int) (d*sprt);
		return (double)tempd/sprt;
	}

	public static String substring(String str, int length) {
		if (str == null)
			return null;

		if (str.length() > length)
			return (str.substring(0, length - 2) + "...");
		else
			return str;
	}
	/**
	 * 字符串全文替换
	 *
	 * @param s0
	 * @param oldstr
	 * @param newstr
	 * @return
	 */
	public static String replaceAll(String s0, String oldstr, String newstr) {
		if (s0 == null || s0.trim().equals(""))
			return null;
		StringBuffer sb = new StringBuffer(s0);
		int begin = 0;
		// int from = 0;
		begin = s0.indexOf(oldstr);
		while (begin > -1) {
			sb = sb.replace(begin, begin + oldstr.length(), newstr);
			s0 = sb.toString();
			begin = s0.indexOf(oldstr, begin + newstr.length());
		}
		return s0;
	}
	public static  String randomString(int length) {
		if (length < 1) {
			return null;
		}
		 Random randGen = null;
		Object initLock = new Object();
		 char[] numbersAndLetters = null;
		// Init of pseudo random number generator.
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					// Also initialize the numbersAndLetters array
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
							+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
							.toCharArray();
				}
			}
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}
	/**
	 * 验证汉字为true
	 *
	 * @param s
	 * @return
	 */
	public static boolean isLetterorDigit(String s) {
		if (s.equals("") || s == null) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				// if (!Character.isLetter(s.charAt(i))){
				return false;
			}
		}
		// Character.isJavaLetter()
		return true;
	}
	/**
	 * 验证是不是Int validate a int string
	 *
	 * @param str
	 *            the Integer string.
	 * @return true if the str is invalid otherwise false.
	 */
	public static boolean validateInt(String str) {
		if (str == null || str.trim().equals(""))
			return false;

		char c;
		for (int i = 0, l = str.length(); i < l; i++) {
			c = str.charAt(i);
			if (!((c >= '0') && (c <= '9')))
				return false;
		}

		return true;
	}

	/**
	 * 字节码转换成16进制字符串 内部调试使用
	 *
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	/**
	 * 字节码转换成自定义字符串 内部调试使用
	 *
	 * @param b
	 * @return
	 */
	public static String byte2string(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	public static byte[] string2byte(String hs) {
		byte[] b = new byte[hs.length() / 2];
		for (int i = 0; i < hs.length(); i = i + 2) {
			String sub = hs.substring(i, i + 2);
			byte bb = new Integer(Integer.parseInt(sub, 16)).byteValue();
			b[i / 2] = bb;
		}
		return b;
	}
	/**
	 * 得到字符串中某个字符的个数
	 *
	 * @param str
	 *            字符串
	 * @param c
	 *            字符
	 * @return
	 */
	public static final int getCharnum(String str, char c) {
		int num = 0;
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (c == chars[i]) {
				num++;
			}
		}
		return num;
	}

	/**
	 * 判断字符串是否为null或长度为0
	 *
	 * @param s 待校验字符串
	 * @return {@code true}: 空<br> {@code false}: 不为空
	 */
	public static boolean isEmpty(CharSequence s) {
		return s == null || s.length() == 0;
	}

	/**
	 * 判断字符串是否为null或全为空格
	 *
	 * @param s 待校验字符串
	 * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
	 */
	public static boolean isSpace(String s) {
		return (s == null || s.trim().length() == 0);
	}

	/**
	 * null转为长度为0的字符串
	 *
	 * @param s 待转字符串
	 * @return s为null转为长度为0字符串，否则不改变
	 */
	public static String null2Length0(String s) {
		return s == null ? "" : s;
	}

	/**
	 * 返回字符串长度
	 *
	 * @param s 字符串
	 * @return null返回0，其他返回自身长度
	 */
	public static int length(CharSequence s) {
		return s == null ? 0 : s.length();
	}

	/**
	 * 首字母大写
	 *
	 * @param s 待转字符串
	 * @return 首字母大写字符串
	 */
	public static String upperFirstLetter(String s) {
		if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
			return s;
		}
		return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
	}

	/**
	 * 首字母小写
	 *
	 * @param s 待转字符串
	 * @return 首字母小写字符串
	 */
	public static String lowerFirstLetter(String s) {
		if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
			return s;
		}
		return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
	}

	/**
	 * 反转字符串
	 *
	 * @param s 待反转字符串
	 * @return 反转字符串
	 */
	public static String reverse(String s) {
		int len = length(s);
		if (len <= 1) return s;
		int mid = len >> 1;
		char[] chars = s.toCharArray();
		char c;
		for (int i = 0; i < mid; ++i) {
			c = chars[i];
			chars[i] = chars[len - i - 1];
			chars[len - i - 1] = c;
		}
		return new String(chars);
	}
	/**
	 * 修改敏感字符编码
	 *
	 * @param value
	 * @return
	 */
	public static String htmlEncode(String value) {
		String re[][] = { { "<", "&lt;" }, { ">", "&gt;" }, { "\"", "&quot;" },
				{ "\\′", "&acute;" }, { "&", "&amp;" } };

		for (int i = 0; i < 4; i++) {
			value = value.replaceAll(re[i][0], re[i][1]);
		}
		return value;
	}

	/**
	 * 防SQL注入
	 *
	 * @param str
	 * @return
	 */
	public static boolean sql_inj(String str) {
		String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		String inj_stra[] = inj_str.split("|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 转化为半角字符
	 *
	 * @param s 待转字符串
	 * @return 半角字符串
	 */
	public static String toDBC(String s) {
		if (isEmpty(s)) {
			return s;
		}
		char[] chars = s.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			if (chars[i] == 12288) {
				chars[i] = ' ';
			} else if (65281 <= chars[i] && chars[i] <= 65374) {
				chars[i] = (char) (chars[i] - 65248);
			} else {
				chars[i] = chars[i];
			}
		}
		return new String(chars);
	}

	/**
	 * 转化为全角字符
	 *
	 * @param s 待转字符串
	 * @return 全角字符串
	 */
	public static String toSBC(String s) {
		if (isEmpty(s)) {
			return s;
		}
		char[] chars = s.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			if (chars[i] == ' ') {
				chars[i] = (char) 12288;
			} else if (33 <= chars[i] && chars[i] <= 126) {
				chars[i] = (char) (chars[i] + 65248);
			} else {
				chars[i] = chars[i];
			}
		}
		return new String(chars);
	}

	private static int[] pyValue = new int[]{-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242,
			-20230, -20051, -20036, -20032,
			-20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774, -19763, -19756, -19751,
			-19746, -19741, -19739, -19728,
			-19725, -19715, -19540, -19531, -19525, -19515, -19500, -19484, -19479, -19467, -19289, -19288, -19281,
			-19275, -19270, -19263, -19261,
			-19249, -19243, -19242, -19238, -19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006,
			-19003, -18996, -18977, -18961,
			-18952, -18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696,
			-18526, -18518, -18501, -18490,
			-18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184, -18183,
			-18181, -18012, -17997, -17988,
			-17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752, -17733, -17730, -17721,
			-17703, -17701, -17697, -17692,
			-17683, -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427, -17417, -17202, -17185, -16983,
			-16970, -16942, -16915, -16733,
			-16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433,
			-16429, -16427, -16423, -16419,
			-16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171,
			-16169, -16158, -16155, -15959,
			-15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681, -15667, -15661,
			-15659, -15652, -15640, -15631,
			-15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369,
			-15363, -15362, -15183, -15180,
			-15165, -15158, -15153, -15150, -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119,
			-15117, -15110, -15109, -14941,
			-14937, -14933, -14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889,
			-14882, -14873, -14871, -14857,
			-14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384,
			-14379, -14368, -14355, -14353,
			-14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125, -14123, -14122, -14112,
			-14109, -14099, -14097, -14094,
			-14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907, -13906, -13905, -13896, -13894, -13878,
			-13870, -13859, -13847, -13831,
			-13658, -13611, -13601, -13406, -13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359,
			-13356, -13343, -13340, -13329,
			-13326, -13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060,
			-12888, -12875, -12871, -12860,
			-12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556,
			-12359, -12346, -12320, -12300,
			-12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798, -11781,
			-11604, -11589, -11536, -11358,
			-11340, -11339, -11324, -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041, -11038, -11024,
			-11020, -11019, -11018, -11014,
			-10838, -10832, -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329,
			-10328, -10322, -10315, -10309,
			-10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};

	private static String[] pyStr = new String[]{"a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao",
			"bei", "ben", "beng", "bi", "bian",
			"biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai",
			"chan", "chang", "chao", "che",
			"chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci",
			"cong", "cou", "cu", "cuan",
			"cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die",
			"ding", "diu", "dong", "dou", "du",
			"duan", "dui", "dun", "duo", "e", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou",
			"fu", "ga", "gai", "gan", "gang",
			"gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun",
			"guo", "ha", "hai", "han", "hang",
			"hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun",
			"huo", "ji", "jia", "jian",
			"jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan",
			"kang", "kao", "ke", "ken",
			"keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan",
			"lang", "lao", "le", "lei", "leng",
			"li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv", "luan",
			"lue", "lun", "luo", "ma", "mai",
			"man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu",
			"mo", "mou", "mu", "na", "nai",
			"nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning",
			"niu", "nong", "nu", "nv", "nuan",
			"nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao",
			"pie", "pin", "ping", "po", "pu",
			"qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun",
			"ran", "rang", "rao", "re",
			"ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao",
			"se", "sen", "seng", "sha",
			"shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan",
			"shuang", "shui", "shun",
			"shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te",
			"teng", "ti", "tian", "tiao",
			"tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei",
			"wen", "weng", "wo", "wu", "xi",
			"xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya",
			"yan", "yang", "yao", "ye", "yi",
			"yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze",
			"zei", "zen", "zeng", "zha",
			"zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai",
			"zhuan", "zhuang", "zhui",
			"zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};

	/**
	 * 单个汉字转成ASCII码
	 *
	 * @param s 单个汉字字符串
	 * @return 如果字符串长度是1返回的是对应的ascii码，否则返回-1
	 */
	private static int oneCn2ASCII(String s) {
		if (s.length() != 1) return -1;
		int ascii = 0;
		try {
			byte[] bytes = s.getBytes("GB2312");
			if (bytes.length == 1) {
				ascii = bytes[0];
			} else if (bytes.length == 2) {
				int highByte = 256 + bytes[0];
				int lowByte = 256 + bytes[1];
				ascii = (256 * highByte + lowByte) - 256 * 256;
			} else {
				throw new IllegalArgumentException("Illegal resource string");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ascii;
	}

	/**
	 * 单个汉字转成拼音
	 *
	 * @param s 单个汉字字符串
	 * @return 如果字符串长度是1返回的是对应的拼音，否则返回{@code null}
	 */
	private static String oneCn2PY(String s) {
		int ascii = oneCn2ASCII(s);
		if (ascii == -1) return null;
		String ret = null;
		if (0 <= ascii && ascii <= 127) {
			ret = String.valueOf((char) ascii);
		} else {
			for (int i = pyValue.length - 1; i >= 0; i--) {
				if (pyValue[i] <= ascii) {
					ret = pyStr[i];
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * 获得第一个汉字首字母
	 *
	 * @param s 单个汉字字符串
	 * @return 拼音
	 */
	public static String getPYFirstLetter(String s) {
		if (isSpace(s)) return "";
		String first, py;
		first = s.substring(0, 1);
		py = oneCn2PY(first);
		if (py == null) return null;
		return py.substring(0, 1);
	}

	/**
	 * 中文转拼音
	 *
	 * @param s 汉字字符串
	 * @return 拼音
	 */
	public static String cn2PY(String s) {
		String hz, py;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			hz = s.substring(i, i + 1);
			py = oneCn2PY(hz);
			if (py == null) {
				py = "?";
			}
			sb.append(py);
		}
		return sb.toString();
	}
}
