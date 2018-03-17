package br.com.projeto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cities")
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "zip_code")
	private Integer zipCode;

	@Column(name = "federal_unit")
	private String federalUnit;
        
	@Column(name = "name")
	private String name;        

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getZipCode() {
            return zipCode;
        }

        public void setZipCode(Integer zipCode) {
            this.zipCode = zipCode;
        }

        public String getFederalUnit() {
            return federalUnit;
        }

        public void setFederalUnit(String federalUnit) {
            this.federalUnit = federalUnit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }   
}