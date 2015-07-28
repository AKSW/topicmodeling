package org.aksw.simba.topicmodeling.lang;

import java.io.Serializable;

public class Term implements Serializable {

    private static final long serialVersionUID = 1L;

    public String wordForm;
    public String lemma;
    public String posTag;
    public TermProperties properties = new TermProperties();

    public Term(String wordForm) {
        this.wordForm = wordForm;
        this.lemma = wordForm.toLowerCase();
    }

    public Term(String wordForm, String lemma) {
        this.wordForm = wordForm;
        this.lemma = lemma;
    }

    public Term(String wordForm, String lemma, String posTag) {
        this.wordForm = wordForm;
        this.lemma = lemma;
        this.posTag = posTag;
    }

    public String getWordForm() {
        return wordForm;
    }

    public void setWordForm(String term) {
        this.wordForm = term;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getPosTag() {
        return posTag;
    }

    public void setPosTag(String posTag) {
        this.posTag = posTag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lemma == null) ? 0 : lemma.hashCode());
        result = prime * result + ((posTag == null) ? 0 : posTag.hashCode());
        result = prime * result + ((wordForm == null) ? 0 : wordForm.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Term other = (Term) obj;
        if (lemma == null) {
            if (other.lemma != null)
                return false;
        } else if (!lemma.equals(other.lemma))
            return false;
        if (posTag == null) {
            if (other.posTag != null)
                return false;
        } else if (!posTag.equals(other.posTag))
            return false;
        if (wordForm == null) {
            if (other.wordForm != null)
                return false;
        } else if (!wordForm.equals(other.wordForm))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Term (term=");
        builder.append(wordForm);
        builder.append(", lemma=");
        builder.append(lemma);
        builder.append(", posTag=");
        builder.append(posTag);
        builder.append(")");
        return builder.toString();
    }

}
