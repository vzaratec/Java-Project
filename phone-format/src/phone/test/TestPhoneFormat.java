package phone.test;

/**
 * @author vzaratec86@gmail.com
 */

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.latam.phone.config.LoadProperties;
import com.latam.phone.format.PhoneFormat;
import com.latam.phone.utils.FormatUtils;

public class TestPhoneFormat {

	private final static Logger logger = Logger.getAnonymousLogger();
	private static LoadProperties appConfig = LoadProperties.getInstance();
	private static boolean isDebug = Boolean.valueOf(appConfig.getProperty("debug.enabled"));
	private static FormatUtils formatUtil = new FormatUtils(appConfig, isDebug);

	public static void main(String[] args) {
		logger.log(Level.INFO, "INIT PROCESS");
		for (String isoCountryCode : getCountryList()) {
			if (formatUtil.isCountryEnabled(isoCountryCode)) {
				for (String phoneNumber : getPhoneList()) {
					String formattedPhone = PhoneFormat.getFormattedCellPhone(phoneNumber, isoCountryCode);
					logger.log(Level.INFO, "IsoCountryCode: " + isoCountryCode + " , Country Code: "
							+ appConfig.getProperty(isoCountryCode.concat(".country.phone.code")) + "\nCell Codes => "
							+ appConfig.getProperty(isoCountryCode.concat(".cellphone.codes")) + "\nPhoneNumber In: "
							+ phoneNumber + " PhoneNumber Out: " + formattedPhone + "\n");
				}
			}
		}
		logger.log(Level.INFO, "END PROCESS");
	}

	public static List<String> getPhoneList() {
		List<String> phones = new ArrayList<>();
		phones.add(null);
		phones.add("00568183457238");
		phones.add("00567983457238");
		phones.add("00569983457238");
		phones.add("0569983457238");
		phones.add("569983457238");
		phones.add("09983457238");
		phones.add("9983457238");
		phones.add("8983457238");
		phones.add("0051922223333");
		phones.add("051922223333");
		phones.add("51922223333");
		phones.add("0922223333");
		phones.add("922223333");
		phones.add("0057344445555");
		phones.add("057344445555");
		phones.add("57344445555");
		phones.add("0344445555");
		phones.add("344445555");
		phones.add("0057304445555");
		phones.add("057350445555");
		phones.add("57321445555");
		phones.add("0315445555");
		phones.add("318445555");
		phones.add("0059366667777");
		phones.add("059366667777");
		phones.add("59366667777");
		phones.add("0966667777");
		phones.add("966667777");
		phones.add("0059393967777");
		phones.add("059394906777");
		phones.add("59398337777");
		phones.add("0966667777");
		phones.add("966667777");
		return phones;
	}

	public static List<String> getCountryList() {
		List<String> phones = new ArrayList<>();
		phones.add(null);
		phones.add("CL");
		phones.add("PE");
		phones.add("CO");
		phones.add("EC");
		phones.add("BR");
		phones.add("US");
		return phones;
	}

}
