begin transaction;
alter table categories
    ADD COLUMN IF NOT EXISTS imgfilename varchar(255);
alter table events
    ADD COLUMN IF NOT EXISTS imgfilename varchar(255);
end transaction;