package egovframework.example.sample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SAMPLE")
public class Sample extends SampleDefaultVO {
	
	private static final long serialVersionUID = 1L;
	
	/** ID */
	@Id
	@Column(name="ID")
	private String id; 
	
	/** 이름 */
	@Column(name="NAME")
	private String name;
	
	/** 설명 */
	@Column(name="DESCRIPTION")
	private String description;
	
	/** 사용여부 */
	@Column(name="USE_YN")
	private String useYn;
	
	/** 등록유저 */
	@Column(name="REG_USER")
	private String regUser;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	
}
