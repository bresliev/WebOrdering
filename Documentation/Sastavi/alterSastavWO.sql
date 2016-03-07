alter table ocp_sastav_proizvoda add kolicina_ugradnje number;

update ocp_klasifikacija_proizvoda k
set k."KLASIFIKACIJA#" = '0034'
where k."VRSTA_KLASIFIKACIJE#" = 22
and "PROIZVOD#" in (107204, 334561);

update ocp_vr_atr_proizvod p
set p.vrednost = 'NE', p."KLASIFIKACIJA#" = '0034'
where p."PROIZVOD#" in (107204, 334561)
and p."VRSTA_KLASIFIKACIJE#" = 22
and atribut# = 1820;
