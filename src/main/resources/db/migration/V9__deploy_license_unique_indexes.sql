-- deploy-license unique indexes
create unique index if not exists uk_deploy_license_name on deploy_license(name);
create unique index if not exists uk_deploy_license_md5 on deploy_license(md5) where md5 is not null;