package hdss.input.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hdss.exceptions.HydricDSSException;

public class AmountAvailableInputData {

	private String name;
	private String evaluation;
	private String evaluationDate;
	private double totalRequested;
	
	private Boolean validated;
	
	public String getName() {
		return this.name;
	}

	public String getEvaluation() {
		return this.evaluation;
	}

	public String getEvaluationDate() {
		return this.evaluationDate;
	}

	public double getTotalRequested() {
		return this.totalRequested;
	}
	
	public AmountAvailableInputData (String watershedName, String evaluation, String evaluationDate, double totalRequested) throws HydricDSSException {
	    this.name = watershedName;
		this.evaluation = evaluation;
		this.evaluationDate = evaluationDate;
		this.totalRequested = totalRequested;

	    validated = false;
	    validate();
	}

	public void validate() throws HydricDSSException {
	    	if (!validated) {
	    		validateName();
			validateEvaluation();
	    		validateEvaluationDate();
			validateTotalRequested();
	    		validated = true;
    		}		
	}
	
	private void validateName() throws HydricDSSException {
		if ((this.name.length()>50)||(this.name.length()<1))
		{
			throw (new HydricDSSException ("The input file has no data or does not match the expected format"));
		}
	}

	private void validateEvaluation() throws HydricDSSException {	
		if (this.evaluation != "SHORTAGE" || this.evaluation != "NORMALITY" || this.evaluation != "PLENTY") {
			throw (new HydricDSSException ("The input file has no data or does not match the expected format"));
		}
	}

	private void validateEvaluationDate() throws HydricDSSException {
		if (!this.evaluationDate.equals(getYesterdayDateString())) {
			throw (new HydricDSSException ("The input file has no data or does not match the expected format"));
		}
	}
	
	private void validateTotalRequested() throws HydricDSSException {
		if (this.totalRequested < 0.0) {
			throw (new HydricDSSException ("The input file has no data or does not match the expected format"));			
		}
		
		String s = Double.toString(this.totalRequested);
		String numberParts [] = s.split("\\.");
		
		if(numberParts[1].length() != 2) {
			throw (new HydricDSSException ("The input file has no data or does not match the expected format"));
		}
	}

	private String getYesterdayDateString() {
        	final DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        	return dateFormat.format(yesterday());
	}

	private Date yesterday() {
    		final Calendar cal = Calendar.getInstance();
    		cal.add(Calendar.DATE, -1);
    		return cal.getTime();
	}
	
}
