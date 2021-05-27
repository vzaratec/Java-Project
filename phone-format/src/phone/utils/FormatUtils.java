package phone.utils;

/**
 * @author vzaratec86@gmail.com
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.latam.phone.config.LoadProperties;

public class FormatUtils {

	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private LoadProperties appConfig;
	private boolean isDebug;

	public FormatUtils(LoadProperties appConfig, boolean isDebug) {
		this.appConfig = appConfig;
		this.isDebug = isDebug;
	}
	/**
	 * Method validate if an input country is enabled for this flow
	 * 
	 * @param number
	 * @return
	 */
	public boolean isCountryEnabled(String country) {
		return null != country ? appConfig.getProperty("valid.country.list").contains(country) : false;
	}

	/**
	 * Method clean a input phone number remove chars a left zeros
	 * 
	 * @param number
	 * @return
	 */
	public String cleanPhoneNumber(String number) {
		if (null != number) {
			number = number.replaceAll("\\D", "");
			if (number.length() == 0) {
				number = "0";
			}
			return Long.valueOf(number).toString();
		} else {
			return "0";
		}
	}

	/**
	 * Method validate if the input number match to a cellphone number to the input
	 * country
	 * 
	 * @param number
	 * @param country
	 * @return
	 */
	public boolean validCellPhoneFormat(String number, String country) {
		if (null != number) {
			String exprValidateFormat = appConfig.getProperty(country.concat(".exp.cell.validate.format"));
			Pattern pattern = Pattern.compile(getPhoneExpReg(country, exprValidateFormat));
			Matcher match = pattern.matcher(number);
			if (match.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method take an input number and format it to a sms valid format
	 * 
	 * @param number
	 * @param country
	 * @return
	 */
	public String phoneFormatter(String number, String country) {
		if (!isDebug)
			logger.setLevel(Level.OFF);
		String exprCellCntrCode = appConfig.getProperty(country.concat(".exp.cell.with.country.code.format"));
		String exprCell = appConfig.getProperty(country.concat(".exp.cell.format"));

		Pattern pattern = Pattern.compile(getPhoneExpReg(country, exprCellCntrCode));
		Matcher match = pattern.matcher(number);
		if (match.find()) {
			logger.log(Level.INFO, "match first expr => " + pattern);
			number = "+".concat(number);
			return number;
		} else {
			pattern = Pattern.compile(getPhoneExpReg(country, exprCell));
			match = pattern.matcher(number);
			if (match.find()) {
				logger.log(Level.INFO, "match second expr => " + pattern);
				number = "+".concat(appConfig.getProperty(country.concat(".country.phone.code"))).concat(number);
				return number;
			}
		}
		return number;
	}

	/**
	 * Method obtain a exp reg from properties and set the values of the current
	 * flow
	 * 
	 * @param country
	 * @param expRegValue
	 * @return
	 */
	public String getPhoneExpReg(String country, String expRegValue) {
		expRegValue = expRegValue.replace("countryCode", appConfig.getProperty(country.concat(".country.phone.code")))
				.replace("cellphone", getPhoneCodes(appConfig.getProperty(country.concat(".cellphone.codes"))));
		return expRegValue;
	}

	/**
	 * Method obtain the full list of valid phone codes from the current flow
	 * 
	 * @param phoneCodeKey
	 * @return
	 */
	public String getPhoneCodes(String phoneCodeKey) {
		String[] cellSplit = phoneCodeKey.replace("\\s", "").split(",");
		String allCellCodes = "";
		for (int i = 0; i < cellSplit.length; i++) {
			String codeValue = cellSplit[i].trim();
			if (codeValue.contains("-")) {
				int init = Integer.valueOf(codeValue.substring(0, codeValue.indexOf("-")));
				int end = Integer.valueOf(codeValue.substring(codeValue.indexOf("-") + 1, codeValue.length()));
				for (int rangeCode = init; rangeCode <= end; rangeCode++) {
					allCellCodes = allCellCodes.concat(rangeCode + "|");
				}
			} else {
				allCellCodes = allCellCodes.concat(codeValue + "|");
			}
		}
		allCellCodes = allCellCodes.substring(0, allCellCodes.length() - 1);
		return allCellCodes;
	}

}
