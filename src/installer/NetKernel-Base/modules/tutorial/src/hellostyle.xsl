<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text"/>
	
  <xsl:template match="/message">
    <xsl:apply-templates/>
  </xsl:template>
</xsl:stylesheet> 