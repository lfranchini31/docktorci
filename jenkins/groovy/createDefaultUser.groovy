import hudson.security.*
import jenkins.model.*

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false, false, null)
def credentials = new File("/run/secrets/admin").text.trim().split(":")
hudsonRealm.createAccount(credentials[0], credentials[1])
instance.setSecurityRealm(hudsonRealm)

def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, credentials[0])
instance.setAuthorizationStrategy(strategy)
instance.save()
