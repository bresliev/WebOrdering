package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzDokumentStavka;
import rs.invado.wo.domain.uz.UzDokumentStavkaId;

import java.math.BigDecimal;

/**
 * Home object for domain model class UzDokumentStavka.
 * @see rs.invado.wo.domain.uz.UzDokumentStavka
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class UzDokumentStavkaHome {

	private static final Log log = LogFactory
			.getLog(UzDokumentStavkaHome.class);

	@PersistenceContext
	private EntityManager entityManager;
    
    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }

	public void persist(UzDokumentStavka transientInstance) {
		log.debug("persisting UzDokumentStavka instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UzDokumentStavka persistentInstance) {
		log.debug("removing UzDokumentStavka instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UzDokumentStavka merge(UzDokumentStavka detachedInstance) {
		log.debug("merging UzDokumentStavka instance");
		try {
			UzDokumentStavka result = entityManager.merge(detachedInstance);
            entityManager.flush();
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UzDokumentStavka findById(UzDokumentStavkaId id) {
		log.debug("getting UzDokumentStavka instance with id: " + id);
		try {
			UzDokumentStavka instance = entityManager.find(
					UzDokumentStavka.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

    public UzDokumentStavka insertDokumentStavkaByStoredProcedure(UzDokumentStavkaId id, String statusStavke,
             Short idJediniceMere, Integer proizvod,String nivoKvaliteta,Integer vrstaPromene,Integer idKontrolnogBroja,String idLotaPp,
             BigDecimal isporKol,BigDecimal navedKol,BigDecimal jedinicnaCena,BigDecimal vrednost,Integer idPorudzbenice,
             Short rbStavkePorudzbenice,BigDecimal kolicinaPromener,Short idVdPo,String idDokumentaPo,Short rbStavkePo,
             BigDecimal kolicinaPromenePo,Integer poslovniPartner,BigDecimal jedinicnaTezina,Short rbSarze,String serijskiBroj,
             Integer vrstaProizvoda,Short godina,BigDecimal knjVrednost, Integer idDokumentaOld,BigDecimal rabat){

        return (UzDokumentStavka)getSession().getNamedQuery("inserStavkaSklDokumenta")
                .setParameter("p_id_vd", id.getIdVd())
                .setParameter("p_id_dokumenta",id.getIdDokumenta())
                .setParameter("p_status_stavke", "A")
                .setParameter("p_id_jedinice_mere",idJediniceMere)
                .setParameter("p_proizvod",proizvod)
                .setParameter("p_nivo_kvaliteta",nivoKvaliteta)
                .setParameter("p_vrsta_promene", vrstaPromene)
                .setParameter("p_id_kontrolnog_broja",idKontrolnogBroja)
                .setParameter("p_id_lota_pp",idLotaPp)
                .setParameter("p_ispor_kol",isporKol)
                .setParameter("p_naved_kol",navedKol)
                .setParameter("p_jedinicna_cena",jedinicnaCena)
                .setParameter("p_vrednost",vrednost)
                .setParameter("p_id_porudzbenice",idPorudzbenice)
                .setParameter("p_rb_stavke_porudzbenice",rbStavkePorudzbenice)
                .setParameter("p_kolicina_promener",kolicinaPromener)
                .setParameter("p_id_vd_po",idVdPo)
                .setParameter("p_id_dokumenta_po",idDokumentaPo)
                .setParameter("p_rb_stavke_po",rbStavkePo)
                .setParameter("p_kolicina_promene_po",kolicinaPromenePo)
                .setParameter("p_poslovni_partner#",poslovniPartner)
                .setParameter("p_jedinicna_tezina",jedinicnaTezina)
                .setParameter("p_rb_sarze",rbSarze)
                .setParameter("p_serijskibroj",serijskiBroj)
                .setParameter("p_vrstaproizvoda",vrstaProizvoda)
                .setParameter("p_godina",godina)
                .setParameter("p_rabat",rabat)
                .uniqueResult();
    }
}
