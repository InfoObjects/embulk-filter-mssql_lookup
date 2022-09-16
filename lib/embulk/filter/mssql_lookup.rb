Embulk::JavaPlugin.register_filter(
  "mssql_lookup", "org.embulk.filter.mssql_lookup.MssqlLookupFilterPlugin",
  File.expand_path('../../../../classpath', __FILE__))
