package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTTotalId entity. @author MyEclipse Persistence Tools
 */

public class WkTTotalId implements java.io.Serializable {

	// Fields

	private Integer ktTotal;
	private Integer ktAverageday;
	private Integer ktMostday;
	private Integer ktTotalip;
	private Integer ktAvaragedayip;
	private Integer ktMostdayip;
	private String ktMostscreen;
	private String ktMostarea;
	private String ktMostexplore;
	private String ktMostos;
	private String ktStartdate;
	private String ktEnddate;
	private Long kwId;


	// Constructors

	/** default constructor */
	public WkTTotalId() {
	}

	/** minimal constructor */
	public WkTTotalId(Long kwId,Integer ktTotal ,String ktStartdate, String ktEnddate,Integer ktAverageday, Integer ktMostday,
			Integer ktTotalip, Integer ktAvaragedayip, Integer ktMostdayip,String ktMostscreen, String ktMostarea, String ktMostexplore,
			String ktMostos) {
		this.ktTotal = ktTotal;
		this.kwId=kwId;
		this.ktStartdate = ktStartdate;
		this.ktEnddate = ktEnddate;
		this.ktAverageday = ktAverageday;
		this.ktMostday = ktMostday;
		this.ktTotalip = ktTotalip;
		this.ktAvaragedayip = ktAvaragedayip;
		this.ktMostdayip = ktMostdayip;
		this.ktMostscreen = ktMostscreen;
		this.ktMostarea = ktMostarea;
		this.ktMostexplore = ktMostexplore;
		this.ktMostos = ktMostos;
	}

	/** full constructor */
	public WkTTotalId(Integer ktTotal, Integer ktAverageday, Integer ktMostday,
			Integer ktTotalip, Integer ktAvaragedayip, Integer ktMostdayip,
			String ktMostscreen, String ktMostarea, String ktMostexplore,
			String ktMostos, String ktStartdate, String ktEnddate,Long kwId) {
		this.ktTotal = ktTotal;
		this.ktAverageday = ktAverageday;
		this.ktMostday = ktMostday;
		this.ktTotalip = ktTotalip;
		this.ktAvaragedayip = ktAvaragedayip;
		this.ktMostdayip = ktMostdayip;
		this.ktMostscreen = ktMostscreen;
		this.ktMostarea = ktMostarea;
		this.ktMostexplore = ktMostexplore;
		this.ktMostos = ktMostos;
		this.ktStartdate = ktStartdate;
		this.ktEnddate = ktEnddate;
		this.kwId=kwId;
	}

	// Property accessors

	public Integer getKtTotal() {
		return this.ktTotal;
	}

	public void setKtTotal(Integer ktTotal) {
		this.ktTotal = ktTotal;
	}

	public Integer getKtAverageday() {
		return this.ktAverageday;
	}

	public void setKtAverageday(Integer ktAverageday) {
		this.ktAverageday = ktAverageday;
	}

	public Integer getKtMostday() {
		return this.ktMostday;
	}

	public void setKtMostday(Integer ktMostday) {
		this.ktMostday = ktMostday;
	}

	public Integer getKtTotalip() {
		return this.ktTotalip;
	}

	public void setKtTotalip(Integer ktTotalip) {
		this.ktTotalip = ktTotalip;
	}

	public Integer getKtAvaragedayip() {
		return this.ktAvaragedayip;
	}

	public void setKtAvaragedayip(Integer ktAvaragedayip) {
		this.ktAvaragedayip = ktAvaragedayip;
	}

	public Integer getKtMostdayip() {
		return this.ktMostdayip;
	}

	public void setKtMostdayip(Integer ktMostdayip) {
		this.ktMostdayip = ktMostdayip;
	}

	public String getKtMostscreen() {
		return this.ktMostscreen;
	}

	public void setKtMostscreen(String ktMostscreen) {
		this.ktMostscreen = ktMostscreen;
	}

	public String getKtMostarea() {
		return this.ktMostarea;
	}

	public void setKtMostarea(String ktMostarea) {
		this.ktMostarea = ktMostarea;
	}

	public String getKtMostexplore() {
		return this.ktMostexplore;
	}

	public void setKtMostexplore(String ktMostexplore) {
		this.ktMostexplore = ktMostexplore;
	}

	public String getKtMostos() {
		return this.ktMostos;
	}

	public void setKtMostos(String ktMostos) {
		this.ktMostos = ktMostos;
	}

	public String getKtStartdate() {
		return this.ktStartdate;
	}

	public void setKtStartdate(String ktStartdate) {
		this.ktStartdate = ktStartdate;
	}

	public String getKtEnddate() {
		return this.ktEnddate;
	}

	public void setKtEnddate(String ktEnddate) {
		this.ktEnddate = ktEnddate;
	}
	public Long getKwId()
	{
		return this.kwId;
	}

	public void setKwId(Long kwId)
	{
		this.kwId = kwId;
	}
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WkTTotalId))
			return false;
		WkTTotalId castOther = (WkTTotalId) other;

		return ((this.getKtTotal() == castOther.getKtTotal()) || (this
				.getKtTotal() != null
				&& castOther.getKtTotal() != null && this.getKtTotal().equals(
				castOther.getKtTotal())))
				&& ((this.getKtAverageday() == castOther.getKtAverageday()) || (this
						.getKtAverageday() != null
						&& castOther.getKtAverageday() != null && this
						.getKtAverageday().equals(castOther.getKtAverageday())))
				&& ((this.getKtMostday() == castOther.getKtMostday()) || (this
						.getKtMostday() != null
						&& castOther.getKtMostday() != null && this
						.getKtMostday().equals(castOther.getKtMostday())))
				&& ((this.getKtTotalip() == castOther.getKtTotalip()) || (this
						.getKtTotalip() != null
						&& castOther.getKtTotalip() != null && this
						.getKtTotalip().equals(castOther.getKtTotalip())))
				&& ((this.getKtAvaragedayip() == castOther.getKtAvaragedayip()) || (this
						.getKtAvaragedayip() != null
						&& castOther.getKtAvaragedayip() != null && this
						.getKtAvaragedayip().equals(
								castOther.getKtAvaragedayip())))
				&& ((this.getKtMostdayip() == castOther.getKtMostdayip()) || (this
						.getKtMostdayip() != null
						&& castOther.getKtMostdayip() != null && this
						.getKtMostdayip().equals(castOther.getKtMostdayip())))
				&& ((this.getKtMostscreen() == castOther.getKtMostscreen()) || (this
						.getKtMostscreen() != null
						&& castOther.getKtMostscreen() != null && this
						.getKtMostscreen().equals(castOther.getKtMostscreen())))
				&& ((this.getKtMostarea() == castOther.getKtMostarea()) || (this
						.getKtMostarea() != null
						&& castOther.getKtMostarea() != null && this
						.getKtMostarea().equals(castOther.getKtMostarea())))
				&& ((this.getKtMostexplore() == castOther.getKtMostexplore()) || (this
						.getKtMostexplore() != null
						&& castOther.getKtMostexplore() != null && this
						.getKtMostexplore()
						.equals(castOther.getKtMostexplore())))
				&& ((this.getKtMostos() == castOther.getKtMostos()) || (this
						.getKtMostos() != null
						&& castOther.getKtMostos() != null && this
						.getKtMostos().equals(castOther.getKtMostos())))
				&& ((this.getKtStartdate() == castOther.getKtStartdate()) || (this
						.getKtStartdate() != null
						&& castOther.getKtStartdate() != null && this
						.getKtStartdate().equals(castOther.getKtStartdate())))
				&& ((this.getKtEnddate() == castOther.getKtEnddate()) || (this
						.getKtEnddate() != null
						&& castOther.getKtEnddate() != null && this
						.getKtEnddate().equals(castOther.getKtEnddate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKtTotal() == null ? 0 : this.getKtTotal().hashCode());
		result = 37
				* result
				+ (getKtAverageday() == null ? 0 : this.getKtAverageday()
						.hashCode());
		result = 37 * result
				+ (getKtMostday() == null ? 0 : this.getKtMostday().hashCode());
		result = 37 * result
				+ (getKtTotalip() == null ? 0 : this.getKtTotalip().hashCode());
		result = 37
				* result
				+ (getKtAvaragedayip() == null ? 0 : this.getKtAvaragedayip()
						.hashCode());
		result = 37
				* result
				+ (getKtMostdayip() == null ? 0 : this.getKtMostdayip()
						.hashCode());
		result = 37
				* result
				+ (getKtMostscreen() == null ? 0 : this.getKtMostscreen()
						.hashCode());
		result = 37
				* result
				+ (getKtMostarea() == null ? 0 : this.getKtMostarea()
						.hashCode());
		result = 37
				* result
				+ (getKtMostexplore() == null ? 0 : this.getKtMostexplore()
						.hashCode());
		result = 37 * result
				+ (getKtMostos() == null ? 0 : this.getKtMostos().hashCode());
		result = 37
				* result
				+ (getKtStartdate() == null ? 0 : this.getKtStartdate()
						.hashCode());
		result = 37 * result
				+ (getKtEnddate() == null ? 0 : this.getKtEnddate().hashCode());
		return result;
	}

}