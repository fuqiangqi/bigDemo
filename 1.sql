
 with temp as (
      select * from t_ma_invoice_data   where 
       LENGTH(REPLACE(TRANSLATE( invoice_numb,
                                '-abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ',
                                ' '),
                      ' ',
                      '')) = LENGTH( invoice_numb)  --去除字母与横杠后长度依然不变的我们认为是正常数字的
                      )
select 
        
       tm.xgzbillid,
       tm.sale_name,
       tm.invoice_code,
       tm.invoice_numb,
       t.bostitle      as 签报标题,
       t.bosdate       as 签报日期,
       t.bosnum        as 签报号,
       t.author        as 作者,
       t.Nreserved1    as 总金额
  from t_wfs_bm_faexrb t
  join t_bm_feeprodetail_exrb tb
    on t.bosid = tb.bosid
  join  temp 
   tm
    on to_char(tb.xgzbillid) = to_char(tm.xgzbillid)
 
 where tb.accountid in (3, 4) --限制交通费与经办费
 
 and exists(
 select xgzbillid from (
          select t.invoice_numb ,t.xgzbillid from temp t , temp t2  where t.invoice_numb=t2.invoice_numb+1
        union  
         select  t2.invoice_numb,t2.xgzbillid from temp t , temp t2  where t.invoice_numb=t2.invoice_numb+1
         ) where xgzbillid=tm.xgzbillid)
 
