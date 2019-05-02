<createTable tableName="authors"
  remarks="A table to contain all the authors">
  <column name="id" type="int">
    <constraints nullable="false" primaryKey="true"/>
  </column>
  <column name="name" type="varchar(100)">
    <constraints nullable="false"/>
  </column>
</createTable>