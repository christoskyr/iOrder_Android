package mycompany.iorder;

/**
 * Created by iOrder on 9/11/2014.
 */
public class AccountData {

    private String apiKey;
    private String deviceId;
    private String name;
    //private String login;
    private String password;
    private String email;
    private String refererId;
    private String preyVersion;
    private boolean missing;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

//	public String getLogin() {
//		return login;
//	}
//
//	public void setLogin(String login) {
//		this.login = login;
//	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefererId() {
        return refererId;
    }

    public void setRefererId(String refererId) {
        this.refererId = refererId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreyVersion() {
        return preyVersion;
    }

    public void setPreyVersion(String preyVersion) {
        this.preyVersion = preyVersion;
    }

    public void setMissing(boolean missing) {
        this.missing = missing;
    }

    public boolean isMissing() {
        return missing;
    }

}
