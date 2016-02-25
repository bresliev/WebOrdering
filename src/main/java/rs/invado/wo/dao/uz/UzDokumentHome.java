package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzDokument;
import rs.invado.wo.domain.uz.UzDokumentId;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Home object for domain model class UzDokument.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.domain.uz.UzDokument
 */
@Repository
@Transactional
public class UzDokumentHome {

    static final Log log = LogFactory.getLog(UzDokumentHome.class);

    @PersistenceContext
    EntityManager entityManager;


    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void persist(UzDokument transientInstance) {
        log.debug("persisting UzDokument instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(UzDokument persistentInstance) {
        log.debug("removing UzDokument instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public UzDokument merge(UzDokument detachedInstance) {

        log.debug("merging UzDokument instance");
        try {
            UzDokument result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public UzDokument findById(UzDokumentId id) {
        log.debug("getting UzDokument instance with id: " + id);
        try {
            UzDokument instance = entityManager.find(UzDokument.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public UzDokument findEmptyDokument(String dokument) {

        return (UzDokument) getSession().createCriteria(UzDokument.class)
                .add(Restrictions.isEmpty("uzDokumentStavkas"))
                .add(Restrictions.eq("id.idDokumenta", dokument))
                .uniqueResult();
    }

    public UzDokument insertDokumentByStoredProcedure(Integer idVd, Integer radnikU, String statusDokumenta, Integer nivoPrioriteta, Integer radniNalog,
                                                      Integer radnikS, Integer idSkladista, Integer radnikR, Integer poslovniPartnerKd,
                                                      Integer organizacionaJedinicaKd, Integer organizacionaJedinicaPk, Integer poslovniPartnerPk,
                                                      String adresaIsporukeRobe, Date datumPromene, Date rokVracanja, String spoljniBrojDokumenta,
                                                      String nacinOtpreme, Date datumOvere, Date datumIVremeNki, Date datumIVremeNri, String statusDokumentaPo,
                                                      Integer organizacionaJedinicaC, Integer idCenovnik, Integer idKlasaCene, Integer idSkladista2, Integer organizacionaJedinicaReal,
                                                      Integer radnikTp, Integer radnikOv, Integer idVdOt, Integer poslovniPartnerOt, Integer godinaOt,
                                                      Date datumDokumentaOt, Integer idVdVrste, Integer proizvod, Integer rbSerije, Integer idCiklusa,
                                                      Integer idRbRn, Integer idSkladistaZa, String radnikZ, Integer vlasnikPp, Integer brojRnOdr, Integer idVdOdr,
                                                      Integer organizacionaJedinicaOdr, Integer godinaOdr, Integer vrstaStavke, Integer godina, String napomena,
                                                      String idDokumentaUg, Integer organizacionaJedinicaUg, Integer idVdUg, Integer godinaUg, Integer vrstaPrevoza,
                                                      Integer proizvodJedOdrRef) {


        return (UzDokument) getSession().getNamedQuery("inserSklDokumentNull")
                .setParameter("p_id_vd", idVd)
                .setParameter("p_radnik_u", radnikU)
                .setParameter("p_status_dokumenta", statusDokumenta)
                .setParameter("p_radnik_s", radnikS)
                .setParameter("p_id_skladista", idSkladista)
                .setParameter("p_poslovni_partner_kd", poslovniPartnerKd)
                .setParameter("p_organizaciona_jedinica_kd", organizacionaJedinicaKd)
                .setParameter("p_organizaciona_jedinica_pk", organizacionaJedinicaPk)
                .setParameter("p_poslovni_partner_pk", poslovniPartnerPk)
                .setParameter("p_adresa_isporuke_robe", adresaIsporukeRobe)
                .setParameter("p_datum_promene", datumPromene)
                .setParameter("p_spoljni_broj_dokumenta", spoljniBrojDokumenta)
                .setParameter("p_datum_overe", datumOvere)
                .setParameter("p_organizaciona_jedinica_c", organizacionaJedinicaC)
                .setParameter("p_id_cenovnik", idCenovnik)
                .setParameter("p_id_klasa_cene", idKlasaCene)
                .setParameter("p_organizaciona_jedinica_real", organizacionaJedinicaReal)
                .setParameter("p_radnik_tp", radnikTp)
                .setParameter("p_radnik_ov", radnikOv)
                .setParameter("p_id_skladista_za", idSkladistaZa)
                .setParameter("p_vrsta_stavke", vrstaStavke)
                .setParameter("p_godina", godina)
                .setParameter("p_vrstaprevoza", vrstaPrevoza).uniqueResult();
    }

    public UzDokument chkUzDokument(String idDok) {

        String namedQ = "chkSklDOkument";
        try {
            UzDokument o = (UzDokument) getSession().getNamedQuery(namedQ)
                    .setString("iddok", idDok)
                    .uniqueResult();
            return o;
        } catch (NoResultException e) {
            e.getStackTrace();
        } catch (NonUniqueResultException e) {
            e.getStackTrace();
        }
        return null;
    }

}
