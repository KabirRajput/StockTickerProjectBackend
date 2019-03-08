package com.legend.stock.dbservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quote", catalog = "stock")
public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "id")
	private Integer id;
	@Column(name = "ticker", nullable = false)
	private String ticker;
	@Column(name = "acquisition_price", nullable = false)
	private double acquisitionPrice;
	@Column(name = "user_id")
	private Integer userId;
	
	public Quote() {
		super();
	}
	public Quote(Integer id, String ticker, double acquisitionPrice) {
		super();
		this.id = id;
		this.ticker = ticker;
		this.acquisitionPrice = acquisitionPrice;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public double getAcquisitionPrice() {
		return acquisitionPrice;
	}
	public void setAcquisitionPrice(double acquisitionPrice) {
		this.acquisitionPrice = acquisitionPrice;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quote other = (Quote) obj;
		if (ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!ticker.equals(other.ticker))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Quote [id=" + id + ", ticker=" + ticker + ", acquisitionPrice=" + acquisitionPrice + "]";
	}

}
