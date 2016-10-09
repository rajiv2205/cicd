import jenkins.model.*
import hudson.security.*

def userId = args[0]
def password = args[1]

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
//instance.setSecurityRealm(hudsonRealm)
hudsonRealm.createAccount(userId, password)
instance.setSecurityRealm(hudsonRealm)


//def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
//strategy.setAllowAnonymousRead(false)
def strategy = new GlobalMatrixAuthorizationStrategy()
    instance.setAuthorizationStrategy(strategy)
    strategy.add(Permission.READ, userId)
    strategy.add(Jenkins.ADMINISTER, "admin")
   // instance.setAuthorizationStrategy(strategy)
    instance.save()
//instance.setAuthorizationStrategy(strategy)
//instance.save()




