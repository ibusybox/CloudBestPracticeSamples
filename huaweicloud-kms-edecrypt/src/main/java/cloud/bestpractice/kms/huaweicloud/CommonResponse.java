package cloud.bestpractice.kms.huaweicloud;

public class CommonResponse {
	private int stausCode;

	public int getStausCode() {
		return stausCode;
	}

	public void setStausCode(int stausCode) {
		this.stausCode = stausCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private String content;

}
