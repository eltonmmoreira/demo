# Backend - JAVA
# Getting Started
Clone o repositório:
<pre><code> https://github.com/eltonmmoreira/demo.git</code></pre>

# Tecnologias:
<pre><code> Spring Boot, JPA, H2, JAVA, Hibernate, REST, ehCache, JUnit</code></pre>

# Building and running the application
## Pre-requisites
<pre>
<code>
JAVA 15
Maven
</code>
</pre>

# Documentation
<pre>
<code>
http://localhost:8080/api-docs
http://localhost:8080/swagger-ui.html
</code>
</pre>

# Application
<pre><code>API REST que retorna a cotação de ações da [B3(Bolsa de valores](http://www.b3.com.br/pt_br/)) . 
As informações são buscadas da api [mfinance.com.br](https://mfinance.com.br).
Após a primeira requisição, os dados são mantidos em cache por 5 minutos(cache local com ehcache) .

A Api também prove endpoints para para criação de wallets e ordens de compra e venda, que
atualizam automaticamente a lista de ativos nas wallets informadas nas ordens
com a quantidade, ticker, preço médio, etc...
Api desenvolvida para fins de demonstração, bem como os dados listados.
</code></pre>
