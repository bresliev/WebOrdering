alter table ocp_sastav_proizvoda add kolicina_ugradnje number;

update ocp_klasifikacija_proizvoda k
set k."KLASIFIKACIJA#" = '003401'
where k."VRSTA_KLASIFIKACIJE#" = 22
and "PROIZVOD#" in (107204, 334561);

update ocp_vr_atr_proizvod p
set p.vrednost = 'NE', p."KLASIFIKACIJA#" = '0034'
where p."PROIZVOD#" in (107204, 334561)
and p."VRSTA_KLASIFIKACIJE#" = 22
and atribut# = 1820;


alter table wo_rezervacija add compositearticle number;
alter table wo_rezervacija add compositearticlequantity number;
alter table wo_rezervacija add compositearticlepackquantity number;
alter table wo_rezervacija add compositearticleunitofmeasure number;
alter table wo_rezervacija add compositearticleprice number;

CREATE SEQUENCE WO_SEQ_SASTAV_REZERVACIJA_ID;

CREATE TABLE wo_rezervacija_sastava
    (wo_rezervacija_id              NUMBER,
    proizvod#                      NUMBER,
    id_skladista                   NUMBER,
    kolicina                       NUMBER,
    id_jedinice_mere               NUMBER,
    cena                           NUMBER,
    rabat                          NUMBER,
    ekstra_rabat                   NUMBER,
    kolpopakovanju                 NUMBER);
    
alter table wo_rezervacija_sastava add id number primary key;

alter table wo_rezervacija_sastava add KOLICINA_UGRADNJE number;