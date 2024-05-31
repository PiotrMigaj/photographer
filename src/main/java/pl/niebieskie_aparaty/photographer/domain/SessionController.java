package pl.niebieskie_aparaty.photographer.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {

    @GetMapping("/session-info")
    public Map<String, Object> getSessionInfo(HttpServletRequest request) {
        // Retrieve the HttpSession object from the request
        HttpSession session = request.getSession(false); // Do not create a new session if one doesn't exist

        // Create a map to store session attributes
        Map<String, Object> sessionInfo = new HashMap<>();

        if (session != null) {
            // Get session attributes and put them into the map
            sessionInfo.put("sessionId", session.getId());
            sessionInfo.put("creationTime", session.getCreationTime());
            sessionInfo.put("lastAccessedTime", session.getLastAccessedTime());
            sessionInfo.put("maxInactiveInterval", session.getMaxInactiveInterval());
            sessionInfo.put("isNew", session.isNew());

            // Retrieve and convert session attribute names and values into a Map
            Map<String, Object> attributes = new HashMap<>();
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = session.getAttribute(attributeName);
                attributes.put(attributeName, attributeValue);
            }
            sessionInfo.put("attributes", attributes);
        } else {
            sessionInfo.put("message", "No active session found");
        }

        return sessionInfo;
    }
}

