insert into products (barcode, productname, stock, price, groupId)
values ('123450000000', 'testproduct1', 10, 99.99,
        (select id from prodgroups where groupname = 'testgroup1'));