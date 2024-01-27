
ddl:
	bazel run -- //tools/rules_ebean/src/main/java/internal:ebean_ddl -p=$(CURDIR)/dbrepository-sqlite/src/main/sql -g="codes.showme.domain"
mvn:
	bazel run @unpinned_maven//:pin

