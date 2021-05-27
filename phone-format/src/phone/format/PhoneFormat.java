package phone.format;

/**
 * @author vzaratec86@gmail.com
 */

import java.util.logging.Level;
import java.util.logging.Logger;

import com.latam.phone.config.LoadProperties;
import com.latam.phone.utils.FormatUtils;

public class PhoneFormat {

	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static LoadProperties appConfig = LoadProperties.getInstance();
	private static boolean isDebug = Boolean.valueOf(appConfig.getProperty("debug.enabled"));
	private static FormatUtils formatUtil = new FormatUtils(appConfig, isDebug);

	/**
	 * Method that evaluate an input phone number and if is a match it return it
	 * formatted as a full cell phone number from a input country code. 
	 * Otherwise return the same input phone number.
	 * 
	 * @param phoneNumber
	 * @param isoCountryCdg
	 * @return
	 */
	public static String getFormattedCellPhone(String phoneNumber, String isoCountryCode) {
		if (!isDebug)
			logger.setLevel(Level.OFF);
		if (formatUtil.isCountryEnabled(isoCountryCode)) {
			logger.log(Level.INFO, "Country " + isoCountryCode + " is enabled.");
			String cleanPhone = formatUtil.cleanPhoneNumber(phoneNumber);
			logger.log(Level.INFO, "phone in: " + phoneNumber + " phone out: " + cleanPhone);

			boolean isValidCellPhone = formatUtil.validCellPhoneFormat(cleanPhone, isoCountryCode);
			logger.log(Level.INFO, "isValidCellPhone: " + isValidCellPhone);

			if (isValidCellPhone) {
				String formattedPhone = formatUtil.phoneFormatter(cleanPhone, isoCountryCode);
				logger.log(Level.INFO, "clean phone: " + cleanPhone + " formatted phone: " + formattedPhone);
				return formattedPhone;
			}
			logger.log(Level.INFO, "\n");
		} else {
			logger.log(Level.INFO, "Country " + isoCountryCode + " is NOT enabled.");
		}
		return phoneNumber;
	}

}
