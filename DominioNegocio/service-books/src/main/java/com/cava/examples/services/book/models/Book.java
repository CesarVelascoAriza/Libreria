package com.cava.examples.services.book.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "Libros")
@Data
@AllArgsConstructor

public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long isbn;
	private String title;
	private String subTitle;
	@Temporal(TemporalType.DATE)
	private Date datePublication;
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;
	@Lob
	@JsonIgnore
	private byte[] image;
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	public Book() {
		// TODO Auto-generated constructor stub
	}
	public Book(Long isbn, String title, String subTitle, Date datePublication, BigDecimal price, 
			Categoria categoria) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.subTitle = subTitle;
		this.datePublication = datePublication;
		this.price = price;
		this.categoria = categoria;
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Date getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Integer getImageHasCode() {
		return (this.image != null ) ? image.hashCode():null;
	}
}
