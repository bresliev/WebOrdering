select p.PROIZVOD#
from wo_partner_settings w, Ocp_Proizvod p, 
ocp_klasifikacija_proizvoda km, wo_sort_per_class sc, wo_classs_order co, ocp_klasifikacija_proizvoda k, 
ocp_vr_atr_proizvod a,    
WoProdCene c, (select woa.wo_sort_per_class_id wo_sort_per_class_id, vrednost,                    vra.proizvod#, woa.attribute_owner                    
                from wo_sort_per_object_attribute woa, ocp_vr_atr_proizvod vra                    where woa.attribute_id = vra.ATRIBUT#                    
                and woa.attribute_owner = 'CLASS'                    
                union select -1, '0', -2, 'CLASS' from dual) vrattr          
where p.proizvod# = a.proizvod# and a.atribut# = 1840 and  w.poslovni_partner# = 271 and w.id_kompanija_korisnik = 1
and ((exists (select 1                from uz_stanje_zaliha_skladista u                where w.id_skladista = u.id_skladista                and u.proizvod# = p.proizvod#     
           and u.kolicina_po_stanju_z - u.rezervisana_kol >0                UNION                select 1               
            from wo_partner_settings w, wo_map_kompanijska_skladista ks, uz_stanje_zaliha_skladista u                where  w.id_kompanija_korisnik = ks.id_kompanije_korisnik 
                           and w.id_skladista = ks.id_skladista_raspolozivo                and ks.id_skladista_rezervacija = u.id_skladista                
                            and u.proizvod# = p.proizvod#                and u.kolicina_po_stanju_z - u.rezervisana_kol > 0               
                             and ks.raspoloziva_kolicina_u_skl = 'OBASKLADISTA')                and a.vrednost = 'DA')        
                             or (exists (select 1                    from wo_partner_settings w, uz_stanje_zaliha_skladista u, ocp_sastav_proizvoda s, uz_dozv_pakovanja pak
                                                 where   w.id_skladista = u.id_skladista                    and s.proizvod#_ulaz = pak.proizvod_ref                    
                                                 and pak.transportno = 'DA'                     and s.proizvod#_ulaz = p.proizvod#                    
                                                  and u.proizvod#  = s.proizvod#_izlaz                     
                                                  and ((u.kolicina_po_stanju_z - u.rezervisana_kol)/s.kolicina_ugradnje)/pak.kol_po_pakovanju > 1                     
                                                  UNION                     select 1                    
                                                   from wo_partner_settings w,  wo_map_kompanijska_skladista ks, uz_stanje_zaliha_skladista u, ocp_sastav_proizvoda s,
                                                    uz_dozv_pakovanja pak                     
                                                    where  w.id_kompanija_korisnik = ks.id_kompanije_korisnik                    
                                                     and w.id_skladista = ks.id_skladista_raspolozivo                     
                                                     and ks.id_skladista_rezervacija = u.id_skladista                     
                                                     and ks.raspoloziva_kolicina_u_skl = 'OBASKLADISTA'                     
                                                     and s.proizvod#_ulaz = pak.proizvod_ref                    
                                                      and pak.transportno = 'DA'                     
                                                      and s.proizvod#_ulaz = p.proizvod#                     
                                                      and u.proizvod#  = s.proizvod#_izlaz                     
                                                      and ((u.kolicina_po_stanju_z - u.rezervisana_kol)/s.kolicina_ugradnje)/pak.kol_po_pakovanju > 1)                     
                                                      and a.vrednost = 'SASTAV')                    
                                                       or a.vrednost = 'NE') 
                                                       
                                                       and km.vrsta_klasifikacije# = 22 
                                                       and km.klasifikacija# = '000106' 
                                                       and km.proizvod# = p.proizvod# 
                                                       and km.VRSTA_KLASIFIKACIJE# = sc.WO_VRSTA_KLASIFIKACIJE#        
                                                       and km.KLASIFIKACIJA# = sc.WO_KLASIFIKACIJA#        
                                                       and sc.id = co.wo_sort_per_class_id       
                                                       and co.OCP_VRSTA_KLASIFIKACIJE# = k.VRSTA_KLASIFIKACIJE#        
                                                       and co.OCP_KLASIFIKACIJA# = k.KLASIFIKACIJA#       
                                                       and k.proizvod# = km.proizvod# 
                                                        
and c.organizaciona_jedinica# = 19  
and c.id_klasa_cene = 18
and c.id_cenovnik = 18
and c.datum_do is null 
and c.datum_ov is not null 
and c.proizvod# = p.proizvod#  
and c.klasaCene = 'VP' 
and ((sc.id = vrattr.wo_sort_per_class_id and p.proizvod# = vrattr.proizvod#)        or not exists (select 1                         from wo_sort_per_object_attribute c                        where c.wo_sort_per_class_id = sc.id                        and c.attribute_owner = 'CLASS')) 
order by  1
